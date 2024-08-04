const express = require('express');
const app = express();
const port = 3000; // Utilisez le port 3000 pour Express

app.use(express.json());

app.post('/api/login', (req, res) => {
  console.log(req.body);
  // Traitez la requête de login ici
  res.redirect('http://localhost:4200/home/'); // Redirigez après un login réussi
});

app.listen(port, () => {
  console.log('Listening on port ' + port);
});
