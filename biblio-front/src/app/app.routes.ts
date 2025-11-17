import { Routes } from '@angular/router';
import { ListLivres } from './page/list-livres/list-livres';
import { AuteurPage } from './page/auteur/auteur-page/auteur-page';
import { CollectionPage } from './page/collection-page/collection-page';
import { EditeurPage } from './page/editeur-page/editeur-page';
import { PageAccueil } from './page/page-accueil/page-accueil';
import { LoginPage } from './page/login-page/login-page';

export const routes: Routes = [
    {path:'livre',component:ListLivres},
    {path:'auteur',component:AuteurPage},
    {path:'collection',component:CollectionPage},
    {path:'editeur',component:EditeurPage},
    {path:'home',component:PageAccueil},
    {path:'login',component:LoginPage}
];
