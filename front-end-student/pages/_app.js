import { Provider } from "next-auth/client";
import { AppProps } from "next/app";

import { CookiesProvider } from "react-cookie";

const App = ({ Component, pageProps }) => {
  return (
      <Provider session={pageProps.session}>
        <CookiesProvider>
          <Component {...pageProps} />
        </CookiesProvider>
      </Provider>
  );
};

export default App;
