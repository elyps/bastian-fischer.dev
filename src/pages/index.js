import * as React from 'react'
import Layout from './layout'
import { StaticImage } from 'gatsby-plugin-image'

const IndexPage = () => {
	return (
		<Layout pageTitle="Home Page">
			<p>I'm making this by following the Gatsby Tutorial.</p>
			<StaticImage
				alt="cover image"
				src="../images/cover.jpg"
			/>
		</Layout>
	)
}

export const Head = () => <title>Home Page</title>

export default IndexPage