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
		"gatsby-transformer-sharp",
		{
			resolve: "gatsby-source-filesystem",
			options: {
				name: `blog`,
				path: `${__dirname}/src/pages/blog`,
			}
		},
		"gatsby-plugin-mdx",
		{
			resolve: `gatsby-plugin-sass`,
			options: {
				cssLoaderOptions: {
					esModule: false,
					modules: {
						namedExport: false,
					},
				},
			},
		},
		"gatsby-transformer-remark",
		"gatsby-plugin-react-svg"
	],
}
