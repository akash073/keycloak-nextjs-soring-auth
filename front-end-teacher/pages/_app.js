import { Provider } from "next-auth/client";
import { AppProps } from "next/app";
import 'bootstrap/dist/css/bootstrap.min.css'
import Head from 'next/head';
import { CookiesProvider } from "react-cookie";
import {Navbar} from "../components/Navbar";

const App = ({ Component, pageProps }) => {
  return (
      <Provider session={pageProps.session}>
        <CookiesProvider>

          <head>

          </head>
         <Navbar />


          <body>
          <Component {...pageProps} />
          </body>

        </CookiesProvider>
      </Provider>
  );
};

export default App;
