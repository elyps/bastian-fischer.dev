const path = require("path");
const _ = require("lodash");

exports.createPages = async ({ graphql, actions }) => {
  const { createPage } = actions;

  const categoryTemplate = path.resolve("src/templates/category-template.js");
  const tagTemplate = path.resolve("src/templates/tag-template.js");

  const result = await graphql(`
    {
      allMdx {
        edges {
          node {
            frontmatter {
              categories
              tags
              slug
            }
          }
        }
      }
    }
  `);

  const posts = result.data.allMdx.edges;

  // Kategorien und Tags sammeln
  let categories = [];
  let tags = [];

  posts.forEach(({ node }) => {
    if (node.frontmatter.categories) {
      categories = categories.concat(node.frontmatter.categories);
    }
    if (node.frontmatter.tags) {
      tags = tags.concat(node.frontmatter.tags);
    }
  });

  // Einzigartige Kategorien und Tags
  categories = _.uniq(categories);
  tags = _.uniq(tags);

  // Erstelle dynamische Seiten für Kategorien
  categories.forEach((category) => {
    createPage({
      path: `/categories/${_.lowerCase(category).replace(/\s+/g, '-')}/`,
      component: categoryTemplate,
      context: {
        category, // Dies wird im Template verwendet
      },
    });
  });

  // Erstelle dynamische Seiten für Tags
  tags.forEach((tag) => {
    createPage({
      path: `/tags/${_.lowerCase(tag).replace(/\s+/g, '-')}/`,
      component: tagTemplate,
      context: {
        tag, // Dies wird im Template verwendet
      },
    });
  });
};
