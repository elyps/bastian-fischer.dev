import * as React from 'react';
import Layout from '../../components/layout';
import Seo from '../../components/seo';
import { Link, graphql } from 'gatsby';
// Beispiel eines importierten SVG-Icons (oder nutze eine externe URL)
import BlogIcon from '../../images/icons/file-earmark-text-fill.svg';
import 'bootstrap/dist/css/bootstrap.min.css'; // Bootstrap-Styling

const BlogPage = ({ data }) => {
  return (
    <Layout pageTitle="My Blog Posts">
      <div className="container">
        {data.allMdx.nodes.map((node) => {
          const { categories, tags, author } = node.frontmatter;

          return (
            <div className="card mb-4 shadow-sm d-flex flex-row align-items-center" key={node.id}>
              {/* Anstatt des Cover-Bildes wird ein SVG-Icon verwendet */}
              <div className="card-img-left">
                <img src={BlogIcon} alt="Blog post icon" style={{ width: '100px', height: '100px' }} /> {/* Verwende dein SVG-Icon */}
              </div>

              <div className="card-body">
                {/* Titel des Blogposts */}
                <h5 className="card-title">
                  <Link to={`/blog/${node.frontmatter.slug}`}>
                    {node.frontmatter.title}
                  </Link>
                </h5>

                {/* Veröffentlichungsdatum */}
                <p className="card-text">
                  <small className="text-muted">Posted: {node.frontmatter.date}</small>
                </p>

                {/* Autor anzeigen */}
                <div className="author-info mb-3">
                  <strong>Autor: </strong> {author}
                </div>

                {/* Kategorien */}
                <div>
                  <strong>Kategorien: </strong>
                  {categories && categories.map((category, index) => (
                    <Link to={`/categories/${category.toLowerCase().replace(/\s+/g, '')}`} key={index} className="badge bg-secondary me-1">
                      {category}
                    </Link>
                  ))}
                </div>

                {/* Tags */}
                <div>
                  <strong>Tags: </strong>
                  {tags && tags.map((tag, index) => (
                    <Link to={`/tags/${tag.toLowerCase().replace(/\s+/g, '')}`} key={index} className="badge bg-primary me-1">
                      {tag}
                    </Link>
                  ))}
                </div>
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
    allMdx(sort: { frontmatter: { date: DESC }}) {
      nodes {
        frontmatter {
          date(formatString: "MMMM D, YYYY")
          title
          slug
          categories
          tags
          author
        }
        id
      }
    }
  }
`;

export const Head = () => <Seo title="My Blog Posts" />

export default BlogPage;
