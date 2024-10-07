/**
 * @type {import('gatsby').GatsbyConfig}
 */
module.exports = {
	siteMetadata: {
		title: `bastian-fischer.dev`,
		siteUrl: `https://www.bastian-fischer.dev`,
	},
	plugins: [
		"gatsby-plugin-image",
		"gatsby-plugin-sharp",
		"gatsby-transformer-sharp",  // Füge dies hinzu
		{
			resolve: "gatsby-source-filesystem",
			options: {
				name: `blog`,
				path: `${__dirname}/src/pages/blog`,
			}
		},
		"gatsby-plugin-mdx",
	],
}
