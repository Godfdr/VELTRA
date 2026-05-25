package middleware

import (
	"context"
	"net/http"
	"time"
	"github.com/gin-gonic/gin"
	"github.com/redis/go-redis/v9"
)

var rateLimitLuaScript = redis.NewScript(`
	local key = KEYS[1]
	local limit = tonumber(ARGV[1])
	local window = tonumber(ARGV[2])
	local current = redis.call("INCR", key)
	if current == 1 then
		redis.call("EXPIRE", key, window)
	end
	return current
`)

func SecurityRateLimiter(rdb *redis.Client, limit int64, window time.Duration) gin.HandlerFunc {
	return func(c *gin.Context) {
		ctx, cancel := context.WithTimeout(c.Request.Context(), 2*time.Second)
		defer cancel()

		key := "ratelimit:" + c.ClientIP()
		currentCount, err := rateLimitLuaScript.Run(ctx, rdb, []string{key}, limit, int64(window.Seconds())).Int64()
		if err != nil {
			c.AbortWithStatusJSON(http.StatusInternalServerError, gin.H{"error": "Rate limit assessment failure"})
			return
		}

		if currentCount > limit {
			c.Header("Retry-After", string(int(window.Seconds())))
			c.AbortWithStatusJSON(http.StatusTooManyRequests, gin.H{
				"code":    "TOO_MANY_REQUESTS",
				"message": "NFC transaction rate exceeded. Please slow down.",
			})
			return
		}
		c.Next()
	}
}
