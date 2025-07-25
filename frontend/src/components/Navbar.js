import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { User, Menu, X, LogOut } from 'lucide-react';

const Navbar = () => {
  const [isOpen, setIsOpen] = useState(false);
  const navigate = useNavigate();
  
  // Mock authentication state - replace with actual auth context
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [user, setUser] = useState(null);

  const handleLogout = () => {
    setIsAuthenticated(false);
    setUser(null);
    navigate('/');
  };

  const toggleMenu = () => {
    setIsOpen(!isOpen);
  };

  return (
    <nav className="bg-white shadow-lg">
      <div className="container mx-auto px-4">
        <div className="flex justify-between items-center py-4">
          {/* Logo */}
          <Link to="/" className="flex items-center space-x-2">
            <div className="bg-green-600 text-white p-2 rounded-lg">
              <span className="font-bold text-xl">e-NAM</span>
            </div>
            <span className="text-gray-700 font-semibold hidden sm:block">
              National Agriculture Market
            </span>
          </Link>

          {/* Desktop Navigation */}
          <div className="hidden md:flex items-center space-x-8">
            <Link to="/" className="text-gray-700 hover:text-green-600 transition duration-300">
              Home
            </Link>
            <Link to="/auctions" className="text-gray-700 hover:text-green-600 transition duration-300">
              Live Auctions
            </Link>
            <Link to="/dashboard" className="text-gray-700 hover:text-green-600 transition duration-300">
              Dashboard
            </Link>
            
            {!isAuthenticated ? (
              <div className="flex items-center space-x-4">
                <Link 
                  to="/login" 
                  className="text-gray-700 hover:text-green-600 transition duration-300"
                >
                  Login
                </Link>
                <Link 
                  to="/register" 
                  className="bg-green-600 text-white px-6 py-2 rounded-lg hover:bg-green-700 transition duration-300"
                >
                  Register
                </Link>
              </div>
            ) : (
              <div className="relative">
                <button 
                  className="flex items-center space-x-2 text-gray-700 hover:text-green-600"
                  onClick={() => setIsOpen(!isOpen)}
                >
                  <User className="h-5 w-5" />
                  <span>{user?.name || 'User'}</span>
                </button>
                
                {isOpen && (
                  <div className="absolute right-0 mt-2 w-48 bg-white rounded-md shadow-lg py-1 z-50">
                    <Link 
                      to="/profile" 
                      className="block px-4 py-2 text-gray-700 hover:bg-gray-100"
                    >
                      Profile
                    </Link>
                    <Link 
                      to="/my-listings" 
                      className="block px-4 py-2 text-gray-700 hover:bg-gray-100"
                    >
                      My Listings
                    </Link>
                    <Link 
                      to="/my-bids" 
                      className="block px-4 py-2 text-gray-700 hover:bg-gray-100"
                    >
                      My Bids
                    </Link>
                    <button 
                      onClick={handleLogout}
                      className="w-full text-left px-4 py-2 text-gray-700 hover:bg-gray-100 flex items-center space-x-2"
                    >
                      <LogOut className="h-4 w-4" />
                      <span>Logout</span>
                    </button>
                  </div>
                )}
              </div>
            )}
          </div>

          {/* Mobile menu button */}
          <div className="md:hidden">
            <button 
              onClick={toggleMenu}
              className="text-gray-700 hover:text-green-600 focus:outline-none"
            >
              {isOpen ? <X className="h-6 w-6" /> : <Menu className="h-6 w-6" />}
            </button>
          </div>
        </div>

        {/* Mobile Navigation */}
        {isOpen && (
          <div className="md:hidden">
            <div className="px-2 pt-2 pb-3 space-y-1 sm:px-3 border-t">
              <Link 
                to="/" 
                className="block px-3 py-2 text-gray-700 hover:text-green-600 transition duration-300"
                onClick={() => setIsOpen(false)}
              >
                Home
              </Link>
              <Link 
                to="/auctions" 
                className="block px-3 py-2 text-gray-700 hover:text-green-600 transition duration-300"
                onClick={() => setIsOpen(false)}
              >
                Live Auctions
              </Link>
              <Link 
                to="/dashboard" 
                className="block px-3 py-2 text-gray-700 hover:text-green-600 transition duration-300"
                onClick={() => setIsOpen(false)}
              >
                Dashboard
              </Link>
              
              {!isAuthenticated ? (
                <div className="space-y-2 pt-2">
                  <Link 
                    to="/login" 
                    className="block px-3 py-2 text-gray-700 hover:text-green-600 transition duration-300"
                    onClick={() => setIsOpen(false)}
                  >
                    Login
                  </Link>
                  <Link 
                    to="/register" 
                    className="block px-3 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 transition duration-300 text-center"
                    onClick={() => setIsOpen(false)}
                  >
                    Register
                  </Link>
                </div>
              ) : (
                <div className="space-y-2 pt-2 border-t">
                  <div className="px-3 py-2 text-gray-600 text-sm">
                    Welcome, {user?.name || 'User'}
                  </div>
                  <Link 
                    to="/profile" 
                    className="block px-3 py-2 text-gray-700 hover:text-green-600 transition duration-300"
                    onClick={() => setIsOpen(false)}
                  >
                    Profile
                  </Link>
                  <Link 
                    to="/my-listings" 
                    className="block px-3 py-2 text-gray-700 hover:text-green-600 transition duration-300"
                    onClick={() => setIsOpen(false)}
                  >
                    My Listings
                  </Link>
                  <Link 
                    to="/my-bids" 
                    className="block px-3 py-2 text-gray-700 hover:text-green-600 transition duration-300"
                    onClick={() => setIsOpen(false)}
                  >
                    My Bids
                  </Link>
                  <button 
                    onClick={() => {
                      handleLogout();
                      setIsOpen(false);
                    }}
                    className="w-full text-left px-3 py-2 text-red-600 hover:bg-red-50 transition duration-300"
                  >
                    Logout
                  </button>
                </div>
              )}
            </div>
          </div>
        )}
      </div>
    </nav>
  );
};

export default Navbar;