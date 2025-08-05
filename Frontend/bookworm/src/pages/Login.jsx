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
  const parsedResult = JSON.parse(result);

  // Save tokens to localStorage
  localStorage.setItem("accessToken", parsedResult.accessToken);
  localStorage.setItem("refreshToken", parsedResult.refreshToken);
  localStorage.setItem("role", parsedResult.role);
  localStorage.setItem("loginTime", Date.now()); // optional for tracking

  alert("Logged in successfully!");
  loginUser(); // this sets isLoggedIn = true in AuthContext
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
    <div className="min-h-screen w-screen flex items-center justify-center bg-gray-300 text-white px-4">
      <div className="relative w-[900px] h-[650px] bg-gray-800 rounded-2xl overflow-hidden shadow-2xl">
        {/* Form content */}
        <div
          className={`absolute top-0 w-1/2 h-full flex flex-col items-center justify-center px-10 transition-all duration-700 ease-in-out ${isRegistering ? "right-0" : "left-0"
            }`}
        >
          <h2 className="text-2xl font-bold mb-10">
            {isRegistering ? "Register" : "Login"}
          </h2>

          <form className="w-82" onSubmit={handleSubmit}>
            {isRegistering && (
              <>
                <input
                  type="text"
                  name="fullName"
                  placeholder="Full Name"
                  className="input mb-4"
                  required
                />
                <input
                  type="email"
                  name="email"
                  placeholder="Email"
                  className="input mb-4"
                  required
                />
                <input
                  type="text"
                  name="phone"
                  placeholder="Phone"
                  className="input mb-4"
                  required
                />
                <input
                  type="text"
                  name="address"
                  placeholder="Address"
                  className="input mb-4"
                  required
                />
              </>
            )}

            {!isRegistering && (
              <input
                type="email"
                name="email"
                placeholder="Email"
                className="input mb-4"
                required
              />
            )}

            <input
              type="password"
              name="password"
              placeholder="Password"
              className="input mb-4"
              required
            />

            {isRegistering && (
              <input
                type="password"
                name="confirmPassword"
                placeholder="Confirm Password"
                className="input mb-6"
                required
              />
            )}

            {!isRegistering && (
              <div className="flex justify-between text-sm mb-4">
                <label className="flex items-center">
                  <input type="checkbox" className="mr-4" />
                  Remember Me
                </label>
                <a href="#" className="text-white-400 hover:underline">
                  Forgot?
                </a>
              </div>
            )}

            <button type="submit" className="btn w-full mb-8">
              {isRegistering ? "Register" : "Login"}
            </button>
          </form>
        </div>

        {/* Sliding panel */}
        <div
          className={`absolute top-0 w-1/2 h-full bg-[#B7BBC7] text-black flex flex-col justify-center items-center px-10 transition-all duration-700 ease-in-out z-10 rounded-2xl ${isRegistering ? "left-0" : "right-0"
            }`}
        >
          <div className="text-center max-w-[240px]">
            <h2 className="text-3xl font-bold mb-2">
              {isRegistering ? "Welcome Back!" : "New here?"}
            </h2>
            <p className="text-sm mb-4">
              {isRegistering
                ? "Already have an account?"
                : "Don't have an account?"}
            </p>
            <button
              onClick={() => setIsRegistering(!isRegistering)}
              className="bg-white text-indigo-600 font-semibold px-6 py-2 rounded-xl  shadow hover:bg-gray-100 transition"
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
