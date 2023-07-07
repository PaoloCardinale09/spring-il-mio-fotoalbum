INSERT INTO `photos` (`id`, `description`, `title`, `url`, `visible`) VALUES (NULL, 'Sun picture', 'the Sun', 'https://static.bhphotovideo.com/explora/sites/default/files/ts-space-sun-and-solar-viewing-facts-versus-fiction.jpg', b'1'), (NULL, 'the moon picture', 'the Moon', 'https://spaceplace.nasa.gov/review/all-about-the-moon/the-moon-near-side.en.jpg', b'1')


INSERT INTO `categories` (`id`, `description`, `name`) VALUES (NULL, 'galaxy pics', 'Planet'), (NULL, 'Pics of nature', 'Nature');

INSERT INTO `category_photo` (`photo_id`, `category_id`) VALUES ('1', '1'), ('2', '2');

INSERT INTO `users` (`id`, `email`, `first_name`, `last_name`, `password`) VALUES (NULL, 'mariorossi@gmail.com', 'mario', 'rossi', '{noop}ok'), (NULL, 'paolo@gmail.com', 'paolo', 'cardinale', '{noop}ok')

INSERT INTO `roles` (`id`, `name`) VALUES (1, 'ADMIN'), (2, 'USER');

INSERT INTO `users_roles` (`user_id`, `roles_id`) VALUES ('1', '1'), ('2', '2');
