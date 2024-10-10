import * as React from 'react';
import { graphql, Link } from 'gatsby';
import Layout from '../components/layout';

const TagsPage = ({ data }) => {
  const tags = data.allMdx.distinctTags;

  return (
    <Layout pageTitle="All Tags">
      <h2>All Tags</h2>
      <ul>
        {tags.map((tag, index) => (
          <li key={index}>
            <Link to={`/tags/${tag.toLowerCase().replace(/\s+/g, '-')}`}>
              {tag}
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
      distinctTags: distinct(field: {frontmatter: {tags: SELECT}})
    }
  }
`;

export default TagsPage;
