/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.model.PUPPIES
import com.example.androiddevchallenge.model.Puppy
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Puppies")
                }
            )
        }
    ) {
        Surface(color = MaterialTheme.colors.background) {
            NavHost(navController, startDestination = "list") {
                composable("list") { PuppiesList(navController, PUPPIES) }
                composable("details/{name}") { backStackEntry ->
                    PuppyDetails(PUPPIES.find { it.name == backStackEntry.arguments?.getString("name") }!!)
                }
            }
        }
    }
}

@Composable
fun PuppiesList(navController: NavController, puppies: List<Puppy>) {
    LazyColumn {
        items(puppies) { item ->
            PuppyItem(item) {
                navController.navigate("details/${item.name}")
            }
        }
    }
}

@Composable
fun PuppyDetails(puppy: Puppy) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        Card(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painter = painterResource(puppy.image), contentDescription = null, modifier = Modifier.width(150.dp).height(150.dp))
                Spacer(modifier = Modifier.height(20.dp))
                Text(puppy.name)
                Spacer(modifier = Modifier.height(20.dp))
                Text(puppy.breed)
                Spacer(modifier = Modifier.height(20.dp))
                Text(puppy.details, modifier = Modifier.padding(start = 20.dp, end = 20.dp))
            }
        }
    }
}

@Composable
fun PuppyItem(item: Puppy, onItemClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onItemClick(item.name) }
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Image(painter = painterResource(item.image), contentDescription = null)
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                Text(item.name)
            }
        }
    }
}

@Preview("Details light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        Surface(color = MaterialTheme.colors.background) {
            PuppyDetails(PUPPIES[0])
        }
    }
}

@Preview("Details dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colors.background) {
            PuppyDetails(PUPPIES[0])
        }
    }
}
