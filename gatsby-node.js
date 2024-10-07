exports.onCreatePage = async ({ page, actions }) => {
  const { createPage } = actions;

  // extend the timeout to avoid 15s limit
  if (process.env.NODE_ENV === 'development') {
    page.context.timeout = 30000; // Set to 30 seconds
    createPage(page);
  }
};
