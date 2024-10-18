// Step 1: Import React
import * as React from 'react'
import Layout from '../components/layout'
import Seo from '../components/seo'

// Step 2: Define your component
const AboutPage = () => {
  return (
    <Layout pageTitle="About Page">
      
    </Layout>
  )
}

export const Head = () => <Seo title="About Me" />

// Step 3: Export your component
export default AboutPage