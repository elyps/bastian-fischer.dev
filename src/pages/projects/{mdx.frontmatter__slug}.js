import * as React from 'react'
import { Link, graphql } from 'gatsby'
import Layout from '../../components/layout'
import Seo from '../../components/seo'
import { GatsbyImage, getImage } from 'gatsby-plugin-image'

const ProjectPost = ({ data, children }) => {
  const image = getImage(data.mdx.frontmatter.hero_image)
  const { categories, tags } = data.mdx.frontmatter
  
  return (
    <Layout pageTitle={data.mdx.frontmatter.title}>
      <p>{data.mdx.frontmatter.date}</p>

      {/* Kategorien anzeigen */}
      <div>
        <strong>Kategorien: </strong>
        {categories.map((category, index) => (
          <Link to={`/categories/${category.toLowerCase()}`} key={index}>
            {category}
          </Link>
        ))}
      </div>

      {/* Tags anzeigen */}
      <div>
        <strong>Tags: </strong>
        {tags.map((tag, index) => (
          <Link to={`/tags/${tag.toLowerCase()}`} key={index}>
            {tag}
          </Link>
        ))}
      </div>

      {/* Projektbild */}
      <GatsbyImage
        image={image}
        alt={data.mdx.frontmatter.hero_image_alt}
      />
      
      {/* Bildquelle */}
      <p>
        Photo Credit:{" "}
        <a href={data.mdx.frontmatter.hero_image_credit_link}>
          {data.mdx.frontmatter.hero_image_credit_text}
        </a>
      </p>

      {/* Inhalt des Projekts */}
      {children}
    </Layout>
  )
}

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
`

export const Head = ({ data }) => <Seo title={data.mdx.frontmatter.title} />

export default ProjectPost
