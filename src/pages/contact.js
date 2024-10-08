import * as React from 'react';
import Layout from '../components/layout';

const ContactPage = () => {
  const [email, setEmail] = React.useState('');
  const [subject, setSubject] = React.useState('');
  const [message, setMessage] = React.useState('');
  const [currentSlide, setCurrentSlide] = React.useState(0);
  const [showModal, setShowModal] = React.useState(false); // Zustand für Modal-Sichtbarkeit

  // Deaktivieren des automatischen Wechsels
  React.useEffect(() => {
    const carouselElement = document.getElementById('contactFormCarousel');
    if (carouselElement) {
      carouselElement.addEventListener('slide.bs.carousel', function (event) {
        event.preventDefault(); // Verhindere das automatische Durchlaufen
      });
    }
  }, []);

  // Funktion für das Absenden des Formulars
  const handleConfirmSend = async () => {
    setShowModal(false);

    const response = await fetch('/send-email', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ email, subject, message }),
    });

    if (response.ok) {
      alert('Email sent successfully!');
      setEmail('');
      setSubject('');
      setMessage('');
      setCurrentSlide(0);
    } else {
      alert('Error sending email!');
    }
  };

  return (
    <Layout pageTitle="Get in Touch">
      <div className="container" style={{ maxWidth: '800px' }}>
        <h1 className="mb-4 text-center">Let's Connect</h1>

        {/* Slider (Carousel) */}
        <div
          id="contactFormCarousel"
          className="carousel slide bg-light pt-4 pb-4 rounded"
          data-bs-interval="false" // Deaktiviere das automatische Durchlaufen
          data-bs-ride="false"
          style={{ backgroundColor: '#f8f9fa' }}
        >
          <div className="carousel-inner">
            {/* Slide 1: E-Mail */}
            <div className={`carousel-item ${currentSlide === 0 ? 'active' : ''}`}>
              <div className="d-flex justify-content-center align-items-center flex-column">
                <h3>Enter your Email</h3>
                <input
                  type="email"
                  className="form-control my-3"
                  placeholder="Your email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                  style={{ maxWidth: '600px' }}
                />
                <button
                  className="btn btn-primary w-50"
                  onClick={() => setCurrentSlide(1)}
                >
                  Next
                </button>
              </div>
            </div>

            {/* Slide 2: Betreff */}
            <div className={`carousel-item ${currentSlide === 1 ? 'active' : ''}`}>
              <div className="d-flex justify-content-center align-items-center flex-column">
                <h3>Enter the Subject</h3>
                <input
                  type="text"
                  className="form-control my-3"
                  placeholder="Subject"
                  value={subject}
                  onChange={(e) => setSubject(e.target.value)}
                  required
                  style={{ maxWidth: '600px' }}
                />
                <div className="d-flex justify-content-between w-100" style={{ maxWidth: '400px' }}>
                  <button
                    className="btn btn-secondary w-50 me-2"
                    onClick={() => setCurrentSlide(0)}
                  >
                    Back
                  </button>
                  <button
                    className="btn btn-primary w-50"
                    onClick={() => setCurrentSlide(2)}
                  >
                    Next
                  </button>
                </div>
              </div>
            </div>

            {/* Slide 3: Nachricht */}
            <div className={`carousel-item ${currentSlide === 2 ? 'active' : ''}`}>
              <div className="d-flex justify-content-center align-items-center flex-column">
                <h3>Enter your Message</h3>
                <textarea
                  className="form-control my-3"
                  placeholder="Your message"
                  value={message}
                  onChange={(e) => setMessage(e.target.value)}
                  required
                  style={{ minHeight: '200px', maxWidth: '600px' }}
                />
                <div className="d-flex justify-content-between w-100" style={{ maxWidth: '400px' }}>
                  <button
                    className="btn btn-secondary w-50 me-2"
                    onClick={() => setCurrentSlide(1)}
                  >
                    Back
                  </button>
                  <button
                    className="btn btn-success w-50"
                    onClick={() => setShowModal(true)} // Zeige das Modal an
                  >
                    Preview
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Modal für Bestätigung */}
        {showModal && (
          <div className="modal show d-block" tabIndex="-1">
            <div className="modal-dialog">
              <div className="modal-content">
                <div className="modal-header">
                  <h5 className="modal-title">Confirm Your Details</h5>
                  <button type="button" className="btn-close" onClick={() => setShowModal(false)} aria-label="Close"></button>
                </div>
                <div className="modal-body">
                  <p><strong>Email:</strong> {email}</p>
                  <p><strong>Subject:</strong> {subject}</p>
                  <p><strong>Message:</strong> {message}</p>
                  <p>Do you want to send this email?</p>
                </div>
                <div className="modal-footer">
                  <button type="button" className="btn btn-secondary" onClick={() => setShowModal(false)}>Cancel</button>
                  <button type="button" className="btn btn-primary" onClick={handleConfirmSend}>Confirm and Send</button>
                </div>
              </div>
            </div>
          </div>
        )}
      </div>
    </Layout>
  );
};

export default ContactPage;
