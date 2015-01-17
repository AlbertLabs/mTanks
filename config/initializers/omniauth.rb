OmniAuth.config.logger = Rails.logger

Rails.application.config.middleware.use OmniAuth::Builder do
  provider :google_oauth2, '201754719377-8b5tknd5fn31kh7egmdj5t766jat1chf.apps.googleusercontent.com', '9FaYlkCHVuqY0wbwE5adWu8d', {client_options: {ssl: {ca_file: Rails.root.join("cacert.pem").to_s}}}
end