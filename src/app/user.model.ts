export class User {
  id: number;
  nom: string;
  prenom: string;
  email: string;
  role: string;
  active: number;

  constructor() {
    this.id = 0;
    this.nom = '';
    this.prenom = '';
    this.email = '';
    this.role = '';
    this.active = 0;
  }
}
