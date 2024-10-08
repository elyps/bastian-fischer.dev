const express = require('express');
const nodemailer = require('nodemailer');
const bodyParser = require('body-parser');

const app = express();
app.use(bodyParser.json());

app.post('/send-email', async (req, res) => {
  const { email, subject, message } = req.body;

  // SMTP Transport Setup
  let transporter = nodemailer.createTransport({
    host: 'smtp.your-email-provider.com', // SMTP-Server deines Providers
    port: 587,
    secure: false, // true für 465, false für andere Ports
    auth: {
      user: 'your-email@example.com', // SMTP-Benutzername
      pass: 'your-email-password', // SMTP-Passwort
    },
  });

  // E-Mail-Daten
  let mailOptions = {
    from: '"Your Name" <your-email@example.com>', // Absenderadresse
    to: email, // Empfängeradresse
    subject: subject, // Betreff
    text: message, // Nachrichtentext
  };

  try {
    // E-Mail senden
    await transporter.sendMail(mailOptions);
    res.status(200).send('Email sent successfully');
  } catch (error) {
    console.error('Error sending email:', error);
    res.status(500).send('Error sending email');
  }
});

// Server starten
app.listen(5000, () => {
  console.log('Server running on port 5000');
});
