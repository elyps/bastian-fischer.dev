import * as React from 'react'; // Keine Destrukturierung beim Import
import Layout from '../../components/layout';
import Seo from '../../components/seo';
import { Link, graphql } from 'gatsby';
import CardIcon from '../../images/icons/regex.svg';

const ProjectsPage = ({ data }) => {
  // Verwende useState ohne Destrukturierung
  const tagState = React.useState([]);
  const categoryState = React.useState([]);

  const selectedTags = tagState[0]; // Zugriff auf den aktuellen Wert des Tags-States
  const setSelectedTags = tagState[1]; // Zugriff auf die Funktion zum Ändern des Tags-States

  const selectedCategories = categoryState[0]; // Zugriff auf den aktuellen Wert des Kategorien-States
  const setSelectedCategories = categoryState[1]; // Zugriff auf die Funktion zum Ändern des Kategorien-States

  // Alle verfügbaren Tags und Kategorien sammeln
  const allTags = [...new Set(data.allMdx.nodes.flatMap(node => node.frontmatter.tags))];
  const allCategories = [...new Set(data.allMdx.nodes.flatMap(node => node.frontmatter.categories))];

  // Handler für die Tag-Filterung
  const toggleTag = (tag) => {
    setSelectedTags((prevTags) =>
      prevTags.includes(tag) ? prevTags.filter((t) => t !== tag) : [...prevTags, tag]
    );
  };

  // Handler für die Kategorie-Filterung
  const toggleCategory = (category) => {
    setSelectedCategories((prevCategories) =>
      prevCategories.includes(category) ? prevCategories.filter((c) => c !== category) : [...prevCategories, category]
    );
  };

  // Filterfunktion für die Projekte
  const filteredProjects = data.allMdx.nodes.filter((node) => {
    const tagMatch = selectedTags.length === 0 || selectedTags.every((tag) => node.frontmatter.tags.includes(tag));
    const categoryMatch =
      selectedCategories.length === 0 ||
      selectedCategories.every((category) => node.frontmatter.categories.includes(category));
    return tagMatch && categoryMatch;
  });

  return (
    <Layout pageTitle="My Projects">
      <div className="container">
        {/* Kategorie-Filter Buttons */}
        <div className="mb-4">
          <strong>Filter by Category: </strong>
          {allCategories.map((category, index) => (
            <button
              key={index}
              className={`btn btn-sm btn-outline-secondary me-2 ${
                selectedCategories.includes(category) ? 'active' : ''
              }`}
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
              className={`btn btn-sm btn-outline-primary me-2 ${selectedTags.includes(tag) ? 'active' : ''}`}
              onClick={() => toggleTag(tag)}
            >
              {tag}
            </button>
          ))}
        </div>

        {/* Gefilterte Projekte anzeigen */}
        {filteredProjects.map((node) => {
          const { categories, tags } = node.frontmatter;

          return (
            <div className="card mb-4 shadow-sm d-flex flex-row align-items-center" key={node.id} style={{ borderRadius: '10px', overflow: 'hidden' }}>
              {/* Anstatt des Cover-Bildes wird ein SVG-Icon verwendet */}
              <div className="card-img-left" style={{ margin: '10px' }}>
                <CardIcon style={{ fill: 'var(--primary-color)', width: '50px', height: '50px' }} className="ms-3" />
              </div>

              <div className="card-body">
                {/* Titel des Projekts */}
                <h5 className="card-title">
                  <Link to={`/projects/${node.frontmatter.slug}`} className="text-dark" style={{ fontWeight: 'bold', textDecoration: 'none' }}>
                    {node.frontmatter.title}
                  </Link>
                </h5>

                {/* Veröffentlichungsdatum */}
                <p className="card-text mb-1">
                  <small className="text-muted">Posted: {node.frontmatter.date}</small>
                </p>

                {/* Kategorien */}
                <p className="card-text mb-1">
                  <strong>Kategorien: </strong>
                  {categories &&
                    categories.map((category, index) => (
                      <Link
                        to={`/categories/${category.toLowerCase().replace(/\s+/g, '-')}`}
                        key={index}
                        className="badge bg-secondary me-1"
                      >
                        {category}
                      </Link>
                    ))}
                </p>

                {/* Tags */}
                <p className="card-text mb-1">
                  <strong>Tags: </strong>
                  {tags &&
                    tags.map((tag, index) => (
                      <Link
                        to={`/tags/${tag.toLowerCase().replace(/\s+/g, '-')}`}
                        key={index}
                        className="badge bg-primary me-1"
                      >
                        {tag}
                      </Link>
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
      filter: { internal: { contentFilePath: { regex: "/projects/" } } }  # Filtere nach Projekten
      sort: { frontmatter: { date: DESC } }
    ) {
      nodes {
        frontmatter {
          title
          slug
          date(formatString: "MMMM DD, YYYY")
          categories  # Kategorien für Projekte
          tags        # Tags für Projekte
        }
        id
		excerpt
      }
    }
  }
`;


export const Head = () => <Seo title="My Projects" />;

export default ProjectsPage;
