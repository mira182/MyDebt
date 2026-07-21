FROM node:20-alpine AS builder

WORKDIR /usr/src/app

# Copying package files first keeps this layer cached when only source changes.
COPY package.json ./
RUN npm install

# Copy the rest of the application and build the production bundle (Vite -> dist/)
COPY . .
RUN npm run build


# Use nginx as the base image for serving the Vue app
FROM nginx:1.27-alpine

# Copy the built Vue app from the previous stage
COPY --from=builder /usr/src/app/dist /usr/share/nginx/html

# Normalize permissions: the build context may arrive from Windows/SMB with
# broken (000) perms on static assets, which nginx then serves as 403.
RUN chmod -R a+rX /usr/share/nginx/html

# Replace nginx's stock server block (avoids two servers listening on port 80)
COPY mydebt-nginx.conf /etc/nginx/conf.d/default.conf

CMD ["nginx", "-g", "daemon off;"]
