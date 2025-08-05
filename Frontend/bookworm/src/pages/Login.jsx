import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";
const Login = () => {
  const [isRegistering, setIsRegistering] = useState(false);
  const navigate = useNavigate();
  const { loginUser } = useAuth();


  const handleSubmit = async (e) => {
    e.preventDefault();

    const form = e.target;
    const formData = new FormData(form);
    const data = {};

    for (const [key, value] of formData.entries()) {
      data[key] = value;
    }

    if (isRegistering) {
      if (data.password !== data.confirmPassword) {
        alert("Passwords do not match!");
        return;
      }

      const signupPayload = {
        userName: data.fullName,
        userEmail: data.email,
        userPhone: data.phone,
        userAddress: data.address,
        userPassword: data.password,
        role: "USER"
      };

      try {
        const res = await fetch("http://localhost:8080/api/auth/signup", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(signupPayload)
        });

        const result = await res.text();
        console.log(result);
        alert("Registered successfully!");
        setIsRegistering(false);
      } catch (err) {
        console.error("Signup error:", err);
      }
    } else {
      const loginPayload = {
        email: data.email,
        password: data.password
      };

      try {
        const res = await fetch("http://localhost:8080/api/auth/login", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(loginPayload)
        });

        const result = await res.text();
        console.log(result);

        if (res.ok) {

          alert("Logged in successfully!");
          loginUser();
          navigate("/");
        }
        else {
          alert("Invalid Email or Password");
        }
      } catch (err) {

        console.error("Login error:", err);
      }
    }
  };

  return (
    <div className="min-h-screen w-screen flex items-center justify-center bg-gray-900 text-white px-4">
      <div className="relative w-[768px] h-[500px] bg-gray-800 rounded-2xl overflow-hidden shadow-2xl">
        {/* Form content */}
        <div
          className={`absolute top-0 w-1/2 h-full flex flex-col items-center justify-center px-10 transition-all duration-700 ease-in-out ${isRegistering ? "right-0" : "left-0"
            }`}
        >
          <h2 className="text-2xl font-bold mb-6">
            {isRegistering ? "Register" : "Login"}
          </h2>

          <form className="w-72" onSubmit={handleSubmit}>
            {isRegistering && (
              <>
                <input
                  type="text"
                  name="fullName"
                  placeholder="Full Name"
                  className="input mb-2"
                  required
                />
                <input
                  type="email"
                  name="email"
                  placeholder="Email"
                  className="input mb-2"
                  required
                />
                <input
                  type="text"
                  name="phone"
                  placeholder="Phone"
                  className="input mb-2"
                  required
                />
                <input
                  type="text"
                  name="address"
                  placeholder="Address"
                  className="input mb-2"
                  required
                />
              </>
            )}

            {!isRegistering && (
              <input
                type="email"
                name="email"
                placeholder="Email"
                className="input mb-2"
                required
              />
            )}

            <input
              type="password"
              name="password"
              placeholder="Password"
              className="input mb-2"
              required
            />

            {isRegistering && (
              <input
                type="password"
                name="confirmPassword"
                placeholder="Confirm Password"
                className="input mb-4"
                required
              />
            )}

            {!isRegistering && (
              <div className="flex justify-between text-sm mb-4">
                <label className="flex items-center">
                  <input type="checkbox" className="mr-2" />
                  Remember Me
                </label>
                <a href="#" className="text-indigo-400 hover:underline">
                  Forgot?
                </a>
              </div>
            )}

            <button type="submit" className="btn w-full">
              {isRegistering ? "Register" : "Login"}
            </button>
          </form>
        </div>

        {/* Sliding panel */}
        <div
          className={`absolute top-0 w-1/2 h-full bg-indigo-600 text-white flex flex-col justify-center items-center px-10 transition-all duration-700 ease-in-out z-10 rounded-2xl ${isRegistering ? "left-0" : "right-0"
            }`}
        >
          <div className="text-center max-w-[240px]">
            <h2 className="text-2xl font-bold mb-2">
              {isRegistering ? "Welcome Back!" : "New here?"}
            </h2>
            <p className="text-sm mb-4">
              {isRegistering
                ? "Already have an account?"
                : "Don't have an account?"}
            </p>
            <button
              onClick={() => setIsRegistering(!isRegistering)}
              className="bg-white text-indigo-600 font-semibold px-6 py-2 rounded shadow hover:bg-gray-100 transition"
            >
              {isRegistering ? "Login" : "Register"}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
