import * as React from 'react';
import { Link, useStaticQuery, graphql } from 'gatsby';

import BlogIcon from '../images/icons/file-earmark-text-fill.svg'; // Blog Icon as React Component
import SiteIcon from '../images/icons/floppy2-fill.svg'; // Site Icon as React Component
import CardIcon from '../images/icons/filetype-js.svg'; // Card Icon as React Component
import SearchIcon from '../images/icons/search-heart-fill.svg'; // Such-Icon als SVG
import LightModeIcon from '../images/icons/sun-fill.svg'; // Beispiel für Light Mode
import DarkModeIcon from '../images/icons/moon-stars-fill.svg'; // Beispiel für Dark Mode
import ColorSwitcherIcon from '../images/icons/palette-fill.svg'; // Beispiel für Primary Color

import { ThemeProvider, useTheme } from './ThemeContext'; // Importiere den ThemeProvider

const Layout = ({ pageTitle, children }) => {
	// Zustand für Light/Dark Mode und Primary Color
	//const [darkMode, setDarkMode] = React.useState(false);
	//const [primaryColor, setPrimaryColor] = React.useState('#0d6efd'); // Default Primary
	const [searchQuery, setSearchQuery] = React.useState('');
	const [filteredPosts, setFilteredPosts] = React.useState([]);
	const [isOffcanvasVisible, setOffcanvasVisible] = React.useState(false);

	const { darkMode, toggleDarkMode, primaryColor, changePrimaryColor } = useTheme();

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

	// Beim Laden des Layouts: Dark Mode & Primary Color aus Local Storage laden
	//	React.useEffect(() => {
	//		const storedDarkMode = localStorage.getItem('darkMode') === 'true';
	//		const storedPrimaryColor = localStorage.getItem('primaryColor');
	//
	//		if (storedDarkMode !== null) setDarkMode(storedDarkMode);
	//		if (storedPrimaryColor) setPrimaryColor(storedPrimaryColor);
	//	}, []);

	// Dark Mode Umschalten
	//	const toggleDarkMode = () => {
	//		setDarkMode(!darkMode);
	//		localStorage.setItem('darkMode', !darkMode);
	//	};

	// Primary Color ändern
	//	const handleColorChange = (color) => {
	//		setPrimaryColor(color);
	//		localStorage.setItem('primaryColor', color);
	//	};

	// Event-Handler für die Suche
	const handleSearch = (event) => {
		event.preventDefault();
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
		<div className={`d-flex flex-column ${darkMode ? 'bg-dark text-white' : 'bg-light text-dark'}`} style={{ minHeight: '100vh' }}>
			{/* Bootstrap Navbar */}
			<nav className={`navbar navbar-expand-lg ${darkMode ? 'navbar-dark bg-dark' : 'navbar-light bg-light'}`}>
				<div className="container-fluid">
					{/* Brand Name with Icon */}
					<Link className="navbar-brand d-flex align-items-center" to="/">
						<SiteIcon style={{ fill: primaryColor, width: '30px', height: '30px' }} className="d-inline-block align-text-top me-2" />
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
								<button className="nav-link dropdown-toggle btn" id="navbarDropdown" data-bs-toggle="dropdown" aria-expanded="false">Blog</button>
								<ul className="dropdown-menu" aria-labelledby="navbarDropdown">
									<li><Link className="dropdown-item" to="/blog">All Posts</Link></li>
									<li><Link className="dropdown-item" to="/categories">All Categories</Link></li>
									<li><Link className="dropdown-item" to="/tags">All Tags</Link></li>
								</ul>
							</li>

							{/* Contact Link */}
							<li className="nav-item">
								<Link className="nav-link" to="/contact">Contact</Link>
							</li>

							{/* Light/Dark Mode Switch */}
							<button className="btn btn-link ms-3" onClick={toggleDarkMode}>
								{/* Bedingte Anzeige des Icons je nach Modus */}
								{darkMode ? (
									<LightModeIcon style={{ fill: '#ffc107', width: '24px', height: '24px' }} />
								) : (
									<DarkModeIcon style={{ fill: '#ffc107', width: '24px', height: '24px' }} />
								)}
							</button>

							{/* Primary Color Switcher */}
							<li className="nav-item dropdown ms-3">
							  {/* Aktuelle Primärfarbe als Icon */}
							  <ColorSwitcherIcon style={{ fill: primaryColor, width: '40px', height: '40px' }} className="nav-link dropdown-toggle btn" id="primaryColorDropdown" data-bs-toggle="dropdown" aria-expanded="false" />
							  
							  {/* Dropdown Menu */}
							  <ul className="dropdown-menu" aria-labelledby="primaryColorDropdown">
							    {/* Blue */}
							    <li>
							      <button className="dropdown-item d-flex align-items-center" onClick={() => changePrimaryColor('#0d6efd')}>
							        <span style={{ backgroundColor: '#0d6efd', width: '15px', height: '15px', borderRadius: '50%', display: 'inline-block', marginRight: '10px' }}></span>
							        Blue
							      </button>
							    </li>

							    {/* Red */}
							    <li>
							      <button className="dropdown-item d-flex align-items-center" onClick={() => changePrimaryColor('#dc3545')}>
							        <span style={{ backgroundColor: '#dc3545', width: '15px', height: '15px', borderRadius: '50%', display: 'inline-block', marginRight: '10px' }}></span>
							        Red
							      </button>
							    </li>

							    {/* Green */}
							    <li>
							      <button className="dropdown-item d-flex align-items-center" onClick={() => changePrimaryColor('#198754')}>
							        <span style={{ backgroundColor: '#198754', width: '15px', height: '15px', borderRadius: '50%', display: 'inline-block', marginRight: '10px' }}></span>
							        Green
							      </button>
							    </li>

							    {/* Yellow */}
							    <li>
							      <button className="dropdown-item d-flex align-items-center" onClick={() => changePrimaryColor('#ffc107')}>
							        <span style={{ backgroundColor: '#ffc107', width: '15px', height: '15px', borderRadius: '50%', display: 'inline-block', marginRight: '10px' }}></span>
							        Yellow
							      </button>
							    </li>
							  </ul>
							</li>

						</ul>

						{/* Such-Icon-Button in der Navbar */}
						<button className="btn btn-link ms-3" onClick={() => setOffcanvasVisible(true)}>
							<SearchIcon style={{ fill: primaryColor, width: '24px', height: '24px' }} />
						</button>
					</div>
				</div>
			</nav>

			{/* Main Content */}
			<main className="container flex-grow-1 mt-4">
				{/* Icon vor dem Titel */}
				<div className="d-flex align-items-center mb-3">
					<BlogIcon style={{ fill: primaryColor, width: '50px', height: '50px' }} className="me-3" />
					<h1>{pageTitle}</h1>
				</div>
				{children}
			</main>

			{/* Footer */}
			<footer className="bg-light text-center py-3 mt-auto">
				<p>&copy; {new Date().getFullYear()} - Your Website</p>
			</footer>

			{/* Offcanvas Sidebar für Suchergebnisse */}
			<div className={`offcanvas offcanvas-end ${isOffcanvasVisible ? 'show' : ''}`} tabIndex="-1" id="offcanvasSearch"
				style={{ visibility: isOffcanvasVisible ? 'visible' : 'hidden' }}>
				<div className="offcanvas-header">
					<h5 className="offcanvas-title">Search</h5>
					<button type="button" className="btn-close" onClick={closeOffcanvas} aria-label="Close"></button>
				</div>

				{/* Suchfeld im Offcanvas */}
				<div className="offcanvas-body">
					<form className="d-flex mb-3" onSubmit={handleSearch}>
						<input
							className="form-control me-2"
							type="search"
							placeholder="Search posts..."
							aria-label="Search"
							value={searchQuery}
							onChange={(e) => setSearchQuery(e.target.value)} // Aktualisiere den Zustand mit der Suchanfrage
						/>
						<button
							className="btn w-25 ms-3 me-3 text-uppercase"
							style={{
								backgroundColor: 'var(--primary-color)', // Verwende die CSS-Variable
								color: '#fff', // Textfarbe auf Weiß
								border: 'none'
							}}
							type="submit">
								<SearchIcon style={{ fill: '#fff', width: '24px', height: '24px' }} />
								&emsp;Search
						</button>
					</form>

					{/* Suchergebnisse */}
					{filteredPosts.length > 0 ? (
						<div>
							{filteredPosts.map(post => (
								<div className="card mb-3 shadow-sm border-0" key={post.frontmatter.slug} style={{ borderRadius: '10px', overflow: 'hidden' }}>
									<div className="d-flex align-items-center">
										{/* Icon für den Post */}
										<CardIcon style={{ fill: primaryColor, width: '50px', height: '50px' }} className="ms-3" />
										{/* Inhalt des Posts */}
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
											{/*<p className="card-text mb-1">
												<strong>Author: </strong>{post.frontmatter.author}
											</p>*/}
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

// Wrap Layout in ThemeProvider
export default function WrappedLayout(props) {
	return (
		<ThemeProvider>
			<Layout {...props} />
		</ThemeProvider>
	);
}