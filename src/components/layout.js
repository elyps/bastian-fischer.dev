import * as React from 'react';
import { Link, useStaticQuery, graphql } from 'gatsby';
import 'bootstrap/dist/css/bootstrap.min.css'; // Bootstrap-Styling
import BlogIcon from '../images/icons/floppy2-fill.svg'; // SVG Icon

const Layout = ({ pageTitle, children }) => {
  // Erweitere die GraphQL-Abfrage, um Kategorien und Tags dynamisch abzurufen
  const data = useStaticQuery(graphql`
    query {
      site {
        siteMetadata {
          title
        }
      }
      allMdx {
        distinctCategories: distinct(field: frontmatter___categories)
        distinctTags: distinct(field: frontmatter___tags)
      }
    }
  `);

  React.useEffect(() => {
    if (typeof window !== 'undefined') {
      // Finde alle Dropdown-Untermenüs
      const dropdownSubmenus = document.querySelectorAll('.dropdown-submenu');

      // Event-Listener hinzufügen
      dropdownSubmenus.forEach((submenu) => {
        submenu.addEventListener('click', function (e) {
          e.stopPropagation(); // Verhindere das Schließen des gesamten Dropdowns
          e.preventDefault();

          // Schließe alle anderen offenen Untermenüs
          dropdownSubmenus.forEach((otherSubmenu) => {
            const otherDropdown = otherSubmenu.querySelector('.dropdown-menu');
            if (otherSubmenu !== submenu && otherDropdown.classList.contains('show')) {
              otherDropdown.classList.remove('show');
            }
          });

          // Öffne/Schließe das angeklickte Untermenü
          const nextSibling = submenu.querySelector('.dropdown-menu');
          if (nextSibling) {
            nextSibling.classList.toggle('show');
          }
        });
      });

      // Verhindere, dass das Hauptmenü schließt, wenn ein Untermenü geöffnet wird
      document.querySelectorAll('.dropdown-menu').forEach((dropdown) => {
        dropdown.addEventListener('click', (e) => {
          e.stopPropagation();
        });
      });
    }
  }, []);

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
              {/* Home Link */}
              <li className="nav-item">
                <Link className="nav-link active" aria-current="page" to="/">Home</Link>
              </li>

              {/* About Link */}
              <li className="nav-item">
                <Link className="nav-link" to="/about">About</Link>
              </li>

			  {/* Blog Dropdown Menu */}
			  <li className="nav-item dropdown">
			    <button 
			      className="nav-link dropdown-toggle btn"  // Verwende btn-Klasse, um es wie einen Link aussehen zu lassen
			      id="navbarDropdown" 
			      data-bs-toggle="dropdown" 
			      aria-expanded="false"
			    >
			      Blog
			    </button>
			    <ul className="dropdown-menu" aria-labelledby="navbarDropdown">
			      <li><Link className="dropdown-item" to="/blog">All Posts</Link></li>
			      
			      {/* Kategorien Übersicht Link */}
			      <li><Link className="dropdown-item" to="/categories">All Categories</Link></li>
			      <li className="dropdown-submenu">
			        <button className="dropdown-item dropdown-toggle btn">Categories</button>
			        <ul className="dropdown-menu">
			          {/* Dynamische Kategorien */}
			          {data.allMdx.distinctCategories.map((category, index) => (
			            <li key={index}>
			              <Link className="dropdown-item" to={`/categories/${category.toLowerCase().replace(/\s+/g, '-')}`}>
			                {category}
			              </Link>
			            </li>
			          ))}
			        </ul>
			      </li>

			      {/* Tags Übersicht Link */}
			      <li><Link className="dropdown-item" to="/tags">All Tags</Link></li>
			      <li className="dropdown-submenu">
			        <button className="dropdown-item dropdown-toggle btn">Tags</button>
			        <ul className="dropdown-menu">
			          {/* Dynamische Tags */}
			          {data.allMdx.distinctTags.map((tag, index) => (
			            <li key={index}>
			              <Link className="dropdown-item" to={`/tags/${tag.toLowerCase().replace(/\s+/g, '-')}`}>
			                {tag}
			              </Link>
			            </li>
			          ))}
			        </ul>
			      </li>
	

                </ul>
              </li>

              {/* Contact Link */}
              <li className="nav-item">
                <Link className="nav-link" to="/contact">Contact</Link>
              </li>
            </ul>

            {/* Search Form */}
            <form className="d-flex ms-3">
              <input className="form-control me-2" type="search" placeholder="Search" aria-label="Search" />
              <button className="btn btn-outline-success" type="submit">Search</button>
            </form>
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
