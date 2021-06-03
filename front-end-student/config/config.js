module.exports = {
    serverRuntimeConfig: {
        // Will only be available on the server side
        mySecret: 'secret',
        keycloak_base_url: `${process.env.KEYCLOAK_BASE_URL}`, // Pass through env variables
    },
    publicRuntimeConfig: {
        // Will be available on both server and client
        staticFolder: '/static',
    },
}