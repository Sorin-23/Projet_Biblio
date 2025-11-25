import { Routes } from '@angular/router';
import { ListLivres } from './page/list-livres/list-livres';
import { AuteurPage } from './page/auteur/auteur-page/auteur-page';
import { CollectionPage } from './page/collection-page/collection-page';
import { EditeurPage } from './page/editeur-page/editeur-page';
import { PageAccueil } from './page/page-accueil/page-accueil';
import { LoginPage } from './page/login-page/login-page';
import { authGuard } from './guard/auth-guard/auth-guard';

export const routes: Routes = [
    {path:'livre',component:ListLivres , canActivate: [authGuard]},
    {path:'auteur',component:AuteurPage , canActivate: [authGuard]},
    {path:'collection',component:CollectionPage , canActivate: [authGuard]},
    {path:'editeur',component:EditeurPage , canActivate: [authGuard]},
    {path:'home',component:PageAccueil , canActivate: [authGuard]},
    {path:'login',component:LoginPage}
];
