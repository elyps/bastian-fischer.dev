import React from 'react';
import { ThemeProvider } from './src/components/ThemeContext'; // Der Pfad zur ThemeContext-Datei

// Wrap the entire app with ThemeProvider (Server-side rendering)
export const wrapRootElement = ({ element }) => (
  <ThemeProvider>
    {element}
  </ThemeProvider>
);
