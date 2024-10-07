import * as React from 'react'
import Layout from '../components/layout'
import { StaticImage } from 'gatsby-plugin-image'
import Seo from '../components/seo'
import { Button } from "react-bootstrap";

const IndexPage = () => {
	return (
		<Layout pageTitle="Home Page">
			<p>I'm making this by following the Gatsby Tutorial.</p>
			<Button variant="primary">Click me</Button>
			<StaticImage
				alt="cover image"
				src="../images/cover.jpg"
			/>
		</Layout>
	)
}

export const Head = () => <Seo title="Home Page" />

export default IndexPage