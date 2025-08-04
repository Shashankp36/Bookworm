import React from 'react';
import { Link } from 'react-router-dom';
import logo from '../assets/logo.png'; // your Bookworm logo

function Header() {
    return (
        <header className="header" style={styles.header}>
            <div className="logo-container" style={styles.logoContainer}>
                <img src={logo} alt="Bookworm Logo" style={styles.logo} />
                <h1 style={styles.title}>Bookworm</h1>
            </div>
            <div className="button-group" style={styles.buttonGroup}>
                <Link to="/login">
                    <button style={styles.button}>Login</button>
                </Link>
                <Link to="/signup">
                    <button style={styles.button}>Sign Up</button>
                </Link>
            </div>
        </header>
    );
}

const styles = {
    header: {
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
        padding: '1rem 2rem',
        backgroundColor: '#f2f2f2',
        borderBottom: '1px solid #ccc'
    },
    logoContainer: {
        display: 'flex',
        alignItems: 'center'
    },
    logo: {
        height: '40px',
        marginRight: '10px'
    },
    title: {
        fontSize: '1.5rem',
        margin: 0
    },
    buttonGroup: {
        display: 'flex',
        gap: '10px'
    },
    button: {
        padding: '0.5rem 1rem',
        fontSize: '1rem',
        cursor: 'pointer'
    }
};

export default Header;
