import NextAuth from 'next-auth'
import Providers from 'next-auth/providers'

const options = {
    providers: [
        Providers.Google({
            clientId: process.env.GOOGLE_CLIENT_ID,
            clientSecret: process.env.GOOGLE_CLIENT_SECRET,
            redirectUrl : "http://localhost:3000/auth/auth/google/callback"
        }),
        {
            id: "keycloak",
            name: "keycloak",
            type: "oauth",
            version: "2.0",
            params: { grant_type: "authorization_code" },
            scope: "openid",
            accessTokenUrl: `${process.env.KEYCLOAK_BASE_URL}/token`,
            requestTokenUrl: `${process.env.KEYCLOAK_BASE_URL}/auth`,
            authorizationUrl: `${process.env.KEYCLOAK_BASE_URL}/auth?response_type=code&prompt=consent`,
            clientId: "next-client",
            clientSecret: process.env.KEYCLOAK_CLIENT_SECRET,
            profileUrl: `${process.env.KEYCLOAK_BASE_URL}/userinfo`,
            profile: (profile) => ({ ...profile, id: profile.sub }),
            //state: false, // Disable the state feature
        }
    ],
    debug: true,
    jwt: {
        encrypt: false
    },
    session: {
        jwt: true
    },
    callbacks: {
        async jwt(token, user, account, profile, isNewUser) {
            // Since you are using Credentials' provider, the data you're persisting
            // _should_ reside in the user here (as far as can I see, since I've just tested it out).
            // This gets called whenever a JSON Web Token is created (once) or updated
            if (user?.type) {
                token.status = user.type
            }
            if (user?.username) {
                token.username = user.username;
            }
            console.log(`jwt and is new user ${isNewUser}`,token);
            //console.log('Token',token);
           // console.log(account);
            if(account) {
             //   console.log(account);
                token.accessToken = account.accessToken;
                token.refreshToken = account.refresh_token;
            }
            return token
        },

        async session(session, token) {
            session.type = token.type;
            session.username = token.username;
            session.accessToken = token.accessToken;
            session.refreshToken = token.refreshToken;
            console.log('session',session);
            return session
        }

        /*redirect: async (url, baseUrl) => {
            console.log('### url, baseUrl \n', url.startsWith(baseUrl), url, baseUrl);
            return url.startsWith(baseUrl) ? url : baseUrl;
        },
        async signIn(user, account, profile) {
            //console.log('signIn',user)
            return true
        },
        async session(session, user) {
           // console.log('session',user)
            return session
        },
        async jwt(token, user, account, profile, isNewUser) {
            console.log('jwt',token)
            return token
        },
        async signOut(user) {
            console.log('signOut',user)
            return false
        },*/

    }

}

export default (req, res) => NextAuth(req, res, options)