// gatsby-browser.js

export const onClientEntry = () => {
  if (process.env.NODE_ENV === "development") {
    const script = document.createElement("script");
    script.src = "http://localhost:8097";
    script.async = true;
    document.head.appendChild(script);
  }
};
