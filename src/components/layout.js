import * as React from 'react';
import { Link, useStaticQuery, graphql } from 'gatsby';
import 'bootstrap/dist/css/bootstrap.min.css'; // Bootstrap-Styling
import BlogIcon from '../images/icons/floppy2-fill.svg'; // SVG Icon

const Layout = ({ pageTitle, children }) => {

  const data = useStaticQuery(graphql`
    query {
      site {
        siteMetadata {
          title
        }
      }
    }
  `);

  return (
    <div>
      {/* Bootstrap Navbar */}
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <div className="container-fluid">
          {/* Brand Name with Icon */}
          <Link className="navbar-brand d-flex align-items-center" to="/">
            <img src={BlogIcon} alt="Site icon" width="30" height="30" className="d-inline-block align-text-top me-2" />
            {data.site.siteMetadata.title}
          </Link>

          {/* Toggle button for mobile view */}
          <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon"></span>
          </button>

          {/* Navbar Links */}
          <div className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav ms-auto">
              <li className="nav-item">
                <Link className="nav-link active" aria-current="page" to="/">Home</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/about">About</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/blog">Blog</Link>
              </li>
            </ul>
          </div>
        </div>
      </nav>

      {/* Main Content */}
      <main className="container mt-4">
        {/* Icon vor dem Titel */}
        <div className="d-flex align-items-center mb-3">
          <img src={BlogIcon} alt="Page icon" width="50" height="50" className="me-3" />
          <h1>{pageTitle}</h1>
        </div>
        {children}
      </main>
    </div>
  );
};

export default Layout;
