// gatsby-browser.js
// This file is where Gatsby expects to find any usage of the Gatsby browser APIs (if any). These allow customization/extension of default Gatsby settings affecting the browser.

import "./src/styles/bootstrap-custom.scss";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import React from 'react';
import { ThemeProvider } from './src/components/ThemeContext'; // Der Pfad zur ThemeContext-Datei

export const onClientEntry = () => {
  if (process.env.NODE_ENV === "development") {
    const script = document.createElement("script");
    script.src = "http://localhost:8097";
    script.async = true;
    document.head.appendChild(script);
  }
};

// Wrap the entire app with ThemeProvider
export const wrapRootElement = ({ element }) => (
  <ThemeProvider>
    {element}
  </ThemeProvider>
);
