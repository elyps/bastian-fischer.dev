import * as React from 'react';
import { Link, useStaticQuery, graphql } from 'gatsby';
import 'bootstrap/dist/css/bootstrap.min.css'; // Bootstrap-Styling
import BlogIcon from '../images/icons/file-earmark-text-fill.svg'; // SVG Icon
import SiteIcon from '../images/icons/floppy2-fill.svg'; // SVG Icon
import 'bootstrap/dist/js/bootstrap.bundle.min.js'; // Für Offcanvas-Funktionalität

const Layout = ({ pageTitle, children }) => {
  // Zustand für die Suchanfrage und Offcanvas-Sichtbarkeit
  const searchState = React.useState('');
  const filteredPostsState = React.useState([]);
  const offcanvasVisibleState = React.useState(false);

  const searchQuery = searchState[0];      // Der aktuelle Wert der Suchanfrage
  const setSearchQuery = searchState[1];   // Die Funktion, um die Suchanfrage zu setzen
  const filteredPosts = filteredPostsState[0];  // Die gefilterten Posts
  const setFilteredPosts = filteredPostsState[1]; // Funktion, um die gefilterten Posts zu setzen
  const isOffcanvasVisible = offcanvasVisibleState[0]; // Offcanvas-Sichtbarkeit
  const setOffcanvasVisible = offcanvasVisibleState[1]; // Funktion, um Offcanvas-Sichtbarkeit zu setzen

  // Dynamische GraphQL-Abfrage
  const data = useStaticQuery(graphql`
    query {
      site {
        siteMetadata {
          title
        }
      }
      allMdx {
        nodes {
          frontmatter {
            title
            slug
            date(formatString: "MMMM D, YYYY")
            categories
            tags
            author
          }
        }
        distinctCategories: distinct(field: {frontmatter: {categories: SELECT}})
        distinctTags: distinct(field: {frontmatter: {tags: SELECT}})
      }
    }
  `);

  // Event-Handler für die Suche
  const handleSearch = (event) => {
    event.preventDefault();
    
    // Filtern der Blogposts basierend auf der Suchanfrage
    const filtered = data.allMdx.nodes.filter(post =>
      post.frontmatter.title.toLowerCase().includes(searchQuery.toLowerCase())
    );
    
    setFilteredPosts(filtered);  // Setze die gefilterten Posts
    setOffcanvasVisible(true);   // Zeige das Offcanvas nach der Suche an
  };

  // Offcanvas schließen
  const closeOffcanvas = () => {
    setOffcanvasVisible(false); // Offcanvas schließen
  };

  return (
    <div>
      {/* Bootstrap Navbar */}
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <div className="container-fluid">
          {/* Brand Name with Icon */}
          <Link className="navbar-brand d-flex align-items-center" to="/">
            <img src={SiteIcon} alt="Site icon" width="30" height="30" className="d-inline-block align-text-top me-2" />
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
            <form className="d-flex ms-3" onSubmit={handleSearch}>
              <input
                className="form-control me-2"
                type="search"
                placeholder="Search"
                aria-label="Search"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)} // Aktualisiere den Zustand mit der Suchanfrage
              />
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

      {/* Offcanvas Sidebar für Suchergebnisse */}
      <div className={`offcanvas offcanvas-end ${isOffcanvasVisible ? 'show' : ''}`} tabIndex="-1" id="offcanvasSearch"
        style={{ visibility: isOffcanvasVisible ? 'visible' : 'hidden', width: '100%' }}>
        <div className="offcanvas-header">
          <h5 className="offcanvas-title">Search Results</h5>
          <button type="button" className="btn-close" onClick={closeOffcanvas} aria-label="Close"></button>
        </div>
        <div className="offcanvas-body">
          {filteredPosts.length > 0 ? (
            <div>
              {filteredPosts.map(post => (
                <div className="card mb-3 shadow-sm border-0" key={post.frontmatter.slug} style={{ borderRadius: '10px', overflow: 'hidden' }}>
                  <div className="d-flex align-items-center">
                    {/* Icon für den Post */}
                    <img src={BlogIcon} alt="Blog post icon" style={{ width: '50px', height: '50px', margin: '10px' }} className="me-3" />
                    <div className="card-body">
                      <h5 className="card-title">
                        <Link to={`/blog/${post.frontmatter.slug}`} className="text-dark" style={{ fontWeight: 'bold', textDecoration: 'none' }}>
                          {post.frontmatter.title}
                        </Link>
                      </h5>
                      <p className="card-text mb-1">
                        <small className="text-muted">Posted: {post.frontmatter.date}</small>
                      </p>
                      <p className="card-text mb-1">
                        <strong>Kategorien: </strong>{post.frontmatter.categories.join(', ')}
                      </p>
                      <p className="card-text mb-1">
                        <strong>Tags: </strong>{post.frontmatter.tags.join(', ')}
                      </p>
                      <p className="card-text mb-1">
                        <strong>Author: </strong>{post.frontmatter.author}
                      </p>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          ) : (
            <p>No posts found for your search.</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default Layout;
