import * as React from 'react';
import { Link, graphql } from 'gatsby';
import Layout from '../../components/layout';
import Seo from '../../components/seo';
import { GatsbyImage, getImage } from 'gatsby-plugin-image';

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

const BlogPost = ({ data, children }) => { 
	const image = getImage(data.mdx.frontmatter.hero_image);
	const { categories, tags } = data.mdx.frontmatter;

	return (
		<Layout pageTitle={data.mdx.frontmatter.title}>
			<p>{data.mdx.frontmatter.date}</p>
			<div>
				<strong>Kategorien: </strong>
				{categories.map((category, index) => (
					<button
						key={index}
						className="btn btn-sm me-2"
						style={{ backgroundColor: getColorForTag(category), color: '#fff' }}
					>
						{category}
					</button>
				))}
			</div>
			<div>
				<strong>Tags: </strong>
				{tags.map((tag, index) => (
					<button
						key={index}
						className="btn btn-sm me-2"
						style={{ backgroundColor: getColorForTag(tag), color: '#fff' }}
					>
						{tag}
					</button>
				))}
			</div>

			{/* Sicherheitsüberprüfung für hero_image und hero_image_alt */}
			{image && (
				<GatsbyImage
					image={image}
					alt={data.mdx.frontmatter.hero_image_alt || "Default Alt Text"} // Fallback falls hero_image_alt fehlt
				/>
			)}

			{/* Sicherheitsüberprüfung für Photo Credit */}
			{data.mdx.frontmatter.hero_image_credit_link && (
				<p>
					Photo Credit:{" "}
					<a href={data.mdx.frontmatter.hero_image_credit_link}>
						{data.mdx.frontmatter.hero_image_credit_text || "Unknown"}
					</a>
				</p>
			)}

			{children}
		</Layout>
	);
};

export const query = graphql`
  query($id: String) {
    mdx(id: {eq: $id}) {
      frontmatter {
        title
        date(formatString: "MMMM DD, YYYY")
        hero_image_alt
        hero_image_credit_link
        hero_image_credit_text
        hero_image {
          childImageSharp {
            gatsbyImageData
          }
        }
        categories
        tags
      }
    }
  }
`;

export const Head = ({ data }) => <Seo title={data.mdx.frontmatter.title} />;

export default BlogPost;
