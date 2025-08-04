import React from 'react';

function Footer() {
    return (
        <footer className="footer">
            <div>
                <h3>About Bookworm</h3>
                <p>Bookworm is your one-stop destination to explore, discover, and shop for your favorite books.</p>
                <p>Personalized, curated, and community-driven.</p>
            </div>
            <div>
                <h3>Quick Links</h3>
                <a href="#">About Us</a><br />
                <a href="#">Contact</a><br />
                <a href="#">Terms & Conditions</a><br />
                <a href="#">Privacy Policy</a>
            </div>
            <div>
                <h3>Get in Touch</h3>
                <p>Email: support@bookworm.com</p>
                <p>Phone: +91-9876543210</p>
                <p>Address: Mumbai, India</p>
            </div>
            <div className="footer-bottom">
                © 2025 Bookworm. All rights reserved.
            </div>
        </footer>
    );
}

export default Footer;
