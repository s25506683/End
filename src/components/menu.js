import React from 'react';

import {Link} from 'react-router-dom';
import { AppBar, Toolbar, Button } from '@material-ui/core/';

export default function MyMenu() {
 
  return (
        <AppBar >
            <Toolbar>
                MyApp
                <Button component={Link} to='/product' color="inherit">product</Button>
                <Button component={Link} to='/employee' color="inherit">employee</Button>
            </Toolbar>
        </AppBar>
  )

}