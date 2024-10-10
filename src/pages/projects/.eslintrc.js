module.exports = {
  parser: 'babel-eslint', // Verwende babel-eslint, um moderne JS-Syntax zu verarbeiten
  extends: ['eslint:recommended', 'plugin:react/recommended'], // Standard-Empfehlungen und React-Regeln
  parserOptions: {
    ecmaVersion: 2020, // Ermöglicht die Verwendung von moderner JavaScript-Syntax (ES2020)
    sourceType: 'module', // Ermöglicht die Verwendung von ECMAScript-Modulen (import/export)
    ecmaFeatures: {
      jsx: true, // Ermöglicht die Verwendung von JSX
    },
  },
  env: {
    browser: true, // Definiert globale Variablen für den Browser
    es2021: true, // Definiert globale Variablen für ECMAScript 2021
  },
  plugins: ['react'], // React-spezifische Linting-Regeln
  rules: {
    'react/prop-types': 0, // Deaktiviert Prop-Typen (optional, falls du diese nicht verwendest)
  },
};
