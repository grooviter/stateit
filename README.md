# StateIT

StateIT is a tool inspired by [Terraform](https://www.terraform.io/). StateIT creates resources and keep their state so it can keep track of them. StateIT basic usage is:

- Declare a plan with some resources to create
- Declare how resources state is going to be stored
- Validate/Apply/Destroy the plan

Thanks to the stored state, StateIT knows what resources have been applied so far, what resources should be removed...etc 

## Documentation

Checkout StateIT documentation at https://grooviter.github.io/stateit

## License

StateIT is licensed under [Apache 2 license](http://www.apache.org/licenses/LICENSE-2.0)