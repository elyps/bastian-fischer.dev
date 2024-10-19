import * as React from 'react'; // Keine Destrukturierung beim Import
import Layout from '../../components/layout';
import Seo from '../../components/seo';
import { Link, graphql } from 'gatsby';
import CardIcon from '../../images/icons/body-text.svg';

const colorPool = [
	'#FF5733', '#33FF57', '#3357FF', '#FF33A1', '#A133FF',
	'#FF8F33', '#33FFC1', '#FFC133', '#33A1FF', '#FF5733',
	'#FFB3B3', '#FF66B3', '#FFB366', '#66FFB3', '#66B3FF',
	'#3366FF', '#B366FF', '#FF66FF', '#FF3399', '#99FF33',
	'#6633FF', '#33FF99', '#FF3399', '#66FF99', '#FF9933',
	'#33CCFF', '#9933FF', '#CCFF33', '#FF3333', '#3333FF',
];

function getColorForTag(tagName) {
	const hash = tagName.split('').reduce((acc, char) => acc + char.charCodeAt(0), 0);
	return colorPool[hash % colorPool.length];
}

const BlogPage = ({ data }) => {
	// Verwende useState ohne Destrukturierung
	const tagState = React.useState([]);
	const categoryState = React.useState([]);

	const selectedTags = tagState[0];      // Zugriff auf den aktuellen Wert des Tags-States
	const setSelectedTags = tagState[1];   // Zugriff auf die Funktion zum Ändern des Tags-States

	const selectedCategories = categoryState[0];     // Zugriff auf den aktuellen Wert des Kategorien-States
	const setSelectedCategories = categoryState[1];  // Zugriff auf die Funktion zum Ändern des Kategorien-States

	// Alle verfügbaren Tags und Kategorien sammeln
	const allTags = [...new Set(data.allMdx.nodes.flatMap(node => node.frontmatter.tags))];
	const allCategories = [...new Set(data.allMdx.nodes.flatMap(node => node.frontmatter.categories))];

	// Handler für die Tag-Filterung
	const toggleTag = (tag) => {
		setSelectedTags((prevTags) =>
			prevTags.includes(tag)
				? prevTags.filter((t) => t !== tag)
				: [...prevTags, tag]
		);
	};

	// Handler für die Kategorie-Filterung
	const toggleCategory = (category) => {
		setSelectedCategories((prevCategories) =>
			prevCategories.includes(category)
				? prevCategories.filter((c) => c !== category)
				: [...prevCategories, category]
		);
	};

	// Filterfunktion für die Artikel
	const filteredPosts = data.allMdx.nodes.filter((node) => {
		const tagMatch =
			selectedTags.length === 0 || selectedTags.every((tag) => node.frontmatter.tags.includes(tag));
		const categoryMatch =
			selectedCategories.length === 0 ||
			selectedCategories.every((category) => node.frontmatter.categories.includes(category));
		return tagMatch && categoryMatch;
	});

	return (
		<Layout pageTitle="My Blog Posts">
			<div className="container">
				{/* Kategorie-Filter Buttons */}
				<div className="mb-4">
					<strong>Filter by Category: </strong>
					{allCategories.map((category, index) => (
						<button
							key={index}
							className={`btn btn-sm me-2 ${selectedCategories.includes(category) ? 'active' : ''}`}
							style={{ backgroundColor: getColorForTag(category), color: '#fff', padding: '0 4px 2px', borderRadius: '5px', marginRight: '5px', fontWeight: 'bold', textTransform: 'uppercase', fontSize: '12px' }}
							onClick={() => toggleCategory(category)}
						>
							{category}
						</button>
					))}
				</div>

				{/* Tag-Filter Buttons */}
				<div className="mb-4">
					<strong>Filter by Tag: </strong>
					{allTags.map((tag, index) => (
						<button
							key={index}
							className={`btn btn-sm me-2 ${selectedTags.includes(tag) ? 'active' : ''}`}
							style={{ backgroundColor: getColorForTag(tag), color: '#fff', padding: '0 4px 2px', borderRadius: '5px', marginRight: '5px', fontWeight: 'bold', textTransform: 'uppercase', fontSize: '12px' }}
							onClick={() => toggleTag(tag)}
						>
							{tag}
						</button>
					))}
				</div>

				{/* Gefilterte Artikel anzeigen */}
				{filteredPosts.map((node) => {
					const { categories, tags } = node.frontmatter;

					return (
						<div className="card mb-4 shadow-sm d-flex flex-row align-items-center" key={node.id} style={{ borderRadius: '10px', overflow: 'hidden' }}>
							{/* Anstatt des Cover-Bildes wird ein SVG-Icon verwendet */}
							<div className="card-img-left" style={{ margin: '10px' }}>
								<CardIcon style={{ fill: 'var(--primary-color)', width: '50px', height: '50px' }} className="ms-3" />
							</div>

							<div className="card-body">
								{/* Titel des Blogposts */}
								<h5 className="card-title">
									<Link to={`/blog/${node.frontmatter.slug}`} className="text-dark" style={{ fontWeight: 'bold', textDecoration: 'none' }}>
										{node.frontmatter.title}
									</Link>
								</h5>

								{/* Veröffentlichungsdatum */}
								<p className="card-text mb-1">
									<small className="text-muted">Posted: {node.frontmatter.date}</small>
								</p>

								{/* Autor anzeigen */}
								{/*<p className="card-text mb-1">
									<strong>Autor: </strong> {author}
								</p>*/}

								{/* Kategorien */}
								<p className="card-text mb-1">
									<strong>Kategorien: </strong>
									{categories &&
										categories.map((category, index) => (
											<button
												key={index}
												className="btn btn-sm me-2"
												style={{ backgroundColor: getColorForTag(category), color: '#fff' }}
											>
												<Link
													to={`/categories/${category.toLowerCase().replace(/\s+/g, '-')}`} // Kategorie-Link
													style={{ color: '#fff', textDecoration: 'none' }} // Stellt sicher, dass der Text weiß bleibt
												>
													{category}
												</Link>
											</button>
										))}
								</p>

								{/* Tags */}
								<p className="card-text mb-1">
									<strong>Tags: </strong>
									{tags &&
										tags.map((tag, index) => (
											<button
												key={index}
												className="btn btn-sm me-2"
												style={{ backgroundColor: getColorForTag(tag), color: '#fff' }}
											>
												<Link
													to={`/tags/${tag.toLowerCase().replace(/\s+/g, '-')}`} // Kategorie-Link
													style={{ color: '#fff', textDecoration: 'none' }} // Stellt sicher, dass der Text weiß bleibt
												>
													{tag}
												</Link>
											</button>
										))}
								</p>
							</div>
						</div>
					);
				})}
			</div>
		</Layout>
	);
};

export const query = graphql`
  query {
    allMdx(
      filter: { internal: { contentFilePath: { regex: "/blog/" } } }  # Filtere nach Blogposts
      sort: { frontmatter: { date: DESC } }
    ) {
      nodes {
        frontmatter {
          title
          slug
          date(formatString: "MMMM DD, YYYY")
          categories  # Stelle sicher, dass Kategorien abgerufen werden
          tags        # Stelle sicher, dass Tags abgerufen werden
        }
        id
		excerpt
      }
    }
  }
`;


export const Head = () => <Seo title="My Blog Posts" />;

export default BlogPage;
