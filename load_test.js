import http from 'k6/http';
import { check, sleep } from 'k6';

// Configure the load test parameters
export const options = {
    stages: [
        { duration: '10s', target: 500 },  // Ramp up to 500 concurrent connections
        { duration: '20s', target: 2000 }, // Spike to 2,000 concurrent NFC taps
        { duration: '10s', target: 0 },    // Ramp down to 0
    ],
    thresholds: {
        http_req_duration: ['p(95)<200'], // 95% of transactions must complete in under 200ms
        http_req_failed: ['rate<0.01'],   // Less than 1% failure rate (excluding rate limits)
    },
};

export default function () {
    const url = 'http://localhost:8080/v1/payments/nfc-tap';

    // Simulate a unique transaction payload for each request
    const payload = JSON.stringify({
        sender_id: `usr_offline_${Math.floor(Math.random() * 10000)}`,
        amount: 50000, // 500.00 NGN in Kobo
        device_signature: 'simulated_crypto_hash_8f9a2b'
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer YOUR_TEST_JWT_TOKEN_HERE'
        },
    };

    const res = http.post(url, payload, params);

    // Validate the engine's response
    check(res, {
        'is status 200 (Success)': (r) => r.status === 200,
        'is status 429 (Rate Limited - Expected under load)': (r) => r.status === 429,
        'transaction completed fast (<100ms)': (r) => r.timings.duration < 100,
    });

    sleep(0.1); // Small delay to simulate real-world physical tap intervals
}
