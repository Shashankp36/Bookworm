import React, { useState } from 'react';

const Signup = () => {
    const [message, setMessage] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();

        const form = e.target;
        const formData = new FormData(form);
        const entries = [...formData.entries()];
        const payload = {};

        for (let i in entries) {
            const [key, value] = entries[i];
            payload[key] = value;
        }

        try {
            const res = await fetch('/api/auth/signup', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(payload)
            });

            const text = await res.text();
            setMessage(text);
        } catch (err) {
            setMessage(`Signup failed: ${err.message}`);
        }
    };

    return (
        <div style={{ padding: '2rem', maxWidth: '500px', margin: '0 auto' }}>
            <h2>Signup</h2>
            <form onSubmit={handleSubmit}>
                <label>
                    Name:
                    <input type="text" name="userName" required />
                </label>
                <br /><br />

                <label>
                    Email:
                    <input type="email" name="userEmail" required />
                </label>
                <br /><br />

                <label>
                    Phone:
                    <input type="tel" name="userPhone" required />
                </label>
                <br /><br />

                <label>
                    Address:
                    <textarea name="userAddress" required />
                </label>
                <br /><br />

                <label>
                    Password:
                    <input type="password" name="userPassword" required />
                </label>
                <br /><br />

                <label>
                    Is Admin:
                    <select name="isAdmin">
                        <option value="false">No</option>
                        <option value="true">Yes</option>
                    </select>
                </label>
                <br /><br />

                <label>
                    Is Active:
                    <select name="isActive">
                        <option value="true">Yes</option>
                        <option value="false">No</option>
                    </select>
                </label>
                <br /><br />

                <button type="submit">Sign Up</button>
            </form>

            {message && (
                <p style={{ marginTop: '20px', color: 'green' }}>{message}</p>
            )}
        </div>
    );
};

export default Signup;
