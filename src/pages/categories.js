import * as React from 'react';
import { graphql, Link } from 'gatsby';
import Layout from '../components/layout';

const CategoriesPage = ({ data }) => {
  const categories = data.allMdx.distinctCategories;

  return (
    <Layout pageTitle="All Categories">
      <h2>All Categories</h2>
      <ul>
        {categories.map((category, index) => (
          <li key={index}>
            <Link to={`/categories/${category.toLowerCase().replace(/\s+/g, '-')}`}>
              {category}
            </Link>
          </li>
        ))}
      </ul>
    </Layout>
  );
};

export const query = graphql`
  query {
    allMdx {
      distinctCategories: distinct(field: frontmatter___categories)
    }
  }
`;

export default CategoriesPage;
