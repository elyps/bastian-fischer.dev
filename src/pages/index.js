// src/pages/index.js

import React from "react";

const UnderConstruction = () => (
  <div style={styles.container}>
    <h1 style={styles.heading}>Diese Seite ist momentan im Umbau!</h1>
    <p style={styles.paragraph}>
      Wir arbeiten fleißig daran, unsere Website zu verbessern. Schau bald wieder vorbei!
    </p>
  </div>
);

export const Head = () => <title>Under Construction</title>

const styles = {
  container: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    minHeight: '100vh',
    backgroundColor: '#f7f7f7',
    textAlign: 'center',
    padding: '20px',
  },
  heading: {
    fontSize: '3rem',
    color: '#333',
  },
  paragraph: {
    fontSize: '1.5rem',
    color: '#555',
    marginBottom: '20px',
  },
  image: {
    maxWidth: '100%',
    height: 'auto',
  },
};

export default UnderConstruction;
