import React from 'react';

const Register = () => {
  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50">
      <div className="max-w-md w-full">
        <h2 className="text-3xl font-bold text-center mb-8">Register for e-NAM</h2>
        <div className="bg-white p-8 rounded-lg shadow-md">
          <p className="text-gray-600 text-center">
            Registration form will be implemented here with user type selection 
            (Farmer, Trader, Commission Agent) and required field validation.
          </p>
        </div>
      </div>
    </div>
  );
};

export default Register;