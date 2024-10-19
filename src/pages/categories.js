import * as React from 'react';
import { graphql, Link } from 'gatsby';
import Layout from '../components/layout';

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

const CategoriesPage = ({ data }) => {
	const categories = data.allMdx.distinctCategories;

	return (
		<Layout pageTitle="Categories">
			<h2>All Categories</h2>
			{categories.map((category, index) => (
				<button
					key={index}
					className="btn btn-sm me-2"
					style={{ backgroundColor: getColorForTag(category), color: '#fff' }}
				>
					{category}
				</button>
			))}
		</Layout>
	);
};

export const query = graphql`
  query {
    allMdx {
      distinctCategories: distinct(field: {frontmatter: {categories: SELECT}})
    }
  }
`;

export default CategoriesPage;
