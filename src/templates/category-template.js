import React from "react";
import { graphql } from "gatsby";
import Layout from "../components/layout";

const CategoryTemplate = ({ data, pageContext }) => {
  const { category } = pageContext;
  const posts = data.allMdx.edges;

  return (
    <Layout>
      <h1>Beiträge in der Kategorie: {category}</h1>
      <ul>
        {posts.map(({ node }) => (
          <li key={node.id}>
            <a href={`/blog/${node.frontmatter.slug}`}>{node.frontmatter.title}</a> {/* Verwende node, um auf die Felder zuzugreifen */}
          </li>
        ))}
      </ul>
    </Layout>
  );
};

// GraphQL-Abfrage für die Kategorie-Seiten
export const pageQuery = graphql`
  query($category: String!) {
    allMdx(
      filter: { frontmatter: { categories: { in: [$category] } } }
    ) {
      edges {
        node {
          id
          frontmatter {
            title
            slug
          }
        }
      }
    }
  }
`;

export default CategoryTemplate;
