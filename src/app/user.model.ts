export interface User{
  id: number;
  nom: string;
  prenom: string;
  email: string;
  role: string;
  qrCodeUrl?: string; // Cette propriété est optionnelle

}

