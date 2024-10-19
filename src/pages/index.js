import * as React from 'react';
import Layout from '../components/layout';
import { StaticImage } from 'gatsby-plugin-image';
import Seo from '../components/seo';

const IndexPage = () => {
  return (
    <Layout pageTitle="bastian-fischer.dev">
      <div className="rectangle-container">
        <div className="rectangle rectangle-1">Bastian</div>
        <div className="rectangle rectangle-2">elyps.</div>
        <div className="rectangle rectangle-3">Fischer</div>
      </div>

    </Layout>
  );
};

export const Head = () => <Seo title="bastian-fischer.dev" />;

export default IndexPage;
