import React from "react";
import { graphql } from "gatsby";
import Layout from "../components/layout";

const TagTemplate = ({ data, pageContext }) => {
	const { tag } = pageContext;
	const posts = data.allMdx.edges;

	return (
		<Layout>
			<h1>Beiträge mit dem Tag: {tag}</h1>
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

export const pageQuery = graphql`
  query($tag: String!) {
    allMdx(filter: { frontmatter: { tags: { in: [$tag] } } }) {
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

export default TagTemplate;
