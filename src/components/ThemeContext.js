import React, { createContext, useContext, useState, useEffect } from 'react';

const ThemeContext = createContext();

export const useTheme = () => useContext(ThemeContext);

export const ThemeProvider = ({ children }) => {
  const [darkMode, setDarkMode] = useState(false);
  const [primaryColor, setPrimaryColor] = useState('#0d6efd');

  useEffect(() => {
    const storedDarkMode = localStorage.getItem('darkMode') === 'true';
    const storedPrimaryColor = localStorage.getItem('primaryColor');
    
    if (storedDarkMode !== null) setDarkMode(storedDarkMode);
    if (storedPrimaryColor) setPrimaryColor(storedPrimaryColor);

    // Set CSS variable for primary color
    document.documentElement.style.setProperty('--primary-color', storedPrimaryColor || '#0d6efd');
  }, []);

  const toggleDarkMode = () => {
    setDarkMode(!darkMode);
    localStorage.setItem('darkMode', !darkMode);
  };

  const changePrimaryColor = (color) => {
    setPrimaryColor(color);
    localStorage.setItem('primaryColor', color);

    // Dynamically set the CSS variable for the primary color
    document.documentElement.style.setProperty('--primary-color', color);
  };

  return (
    <ThemeContext.Provider value={{ darkMode, toggleDarkMode, primaryColor, changePrimaryColor }}>
      {children}
    </ThemeContext.Provider>
  );
};
