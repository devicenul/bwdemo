# Super Tiny Bandwidth API DEMO

## What is this?

This is a small demo created to have a chance to play around with the APIs exposed by [Bandwidth](https://bandwidth.com) 
along with a few other bits of technology.

## What were the goals and constraints?

The goal was to get a SpringBoot app up in a Docker container that would let them send me a message via SMS or make a 
SIP phone call to me. The constraints were I had to do with equipment I had on-hand, software I could get for free (or
very cheaply, i.e. the Bandwidth trial account), and I could only do it in the time I could carve out of a single weekend,
starting on a Friday evening and finishing on Sunday.

## The Components

Software programs/services I used:
- IntelliJ IDE
- Docker Toolbox for Windows
- Chrome (for lots and lots of searches)
- Container Station Docker Manger for QNAP NAS
- GitHub for version control

Primary software frameworks I used:
- Spring Boot / Spring Boot Starter
- Java JDK8
- Thymeleaf
- com.spotify dockerfile-maven-plugin
- com.googlecode.libphonenumber for Directory Number (DN) validation and formatting 
- Swagger for API documentation

Hardware used:
- My desktop Windows 10 PC - development and test deployments
- My SurfaceBook 2 - development
- My two QNAP NAS boxes - Docker Station and final Run Environment
- iPhone + BT Speakers + AppleMusic - ambiance
- HDTV + Xbox One + Rogue One: A Star Wars Story - sanity

## Postmortem
###### Or, what worked and what didn't?

**The Good**

The SpringBoot app and its user facing API all work correctly. The user can submit a message and or call request and
it is properly received, validated, and responses passed back to the user.

The Docker image is built and runs correctly.

The SMS messagse are correctly sent to my mobile number through the Bandwidth REST API.

All-in-all, for a "weekend sprint", it felt satisfyingly productive. 

**The Bad**

Partly due to the constraints on the trial Bandwidth account regarding calls needing to be SIP-to-SIP and how it did not
appear completely clear how to generate a call out to two parties and then bridge them (something that is most likely 
in the API set but not something I had time to get further into), calling was never implemented past the UI stage.

**The Ugly**

Despite having the Docker daemon setup with insecure registries and Container Station properly running a self-signed 
registry, I could never get build Docker images to push from my development machines to the goal Docker environment. I 
was able to save out the image as a tarball and manually import it into Container Station to get my Docker-ized 
application running where I wanted it.

Springfox is not properly exposing the Swagger API docs. Not a problem for the demos functionality but annoying at least.

There is some error checking but not a lot of it and the same goes for logging. Both could stand some enhancing. There 
is no testing to speak of.

## What did I learn?

*Container Station:* I was aware the my NAS boxes supported running certain standard VM image formats via the QNAP Visualization 
Station but had never used it, running all of the 3rd party services I use on my servers as directly installed packages to save
on resources. I had not noticed that *Container Station* was an option until now. I will definitely consider using this 
to migrate some of them to lighter-weight containers with environment isolation in the future.

*Thymeleaf:* Doing most of me development in the past on protocols, system services, and back-ends, I had not really done
much with UI. Since I needed a form to get user input and did not want to spend the time working through building a 
JavaScript-based front-end, the ability to define templates and post data back and forth with POJOs that *Thymeleaf* 
provided was very nice. I can using this in the future when prototyping back-end services when I want a quick interface
to it other than using raw HTTP via cURL or firing up something like Postman/Swagger.

*Libphonenumber:* It had been a long time since I'd thought about validating phone numbers or converting formats. It was 
nice to find a library to do some of the heaving lifting without resorting to potentially piles of regex. It is possible this is
also availble in the REST API or SDKs that Bandwidth provides and I just did not find it quickly enough.

*dockerfile-maven-plugin:* I was familiar with using a Maven plugin from io.fabric8 for building a Docker image that had
been composed via a Dockerfile spec at work but I went looking to see what other alternatives were out there. I found
this plugin from Spotify and it seems pretty capable. The only issue, previously mentioned, was figuring out how to get
it to build/push against *Container Station* directly.

*Bandwidth APIs:* I hesitate to say I learned a great deal about these. I only scratched the surface of what's available
there but I was able to get a little feel for how it worked and would like to revisit it in time now that I have a development
base to play with.

## What next?

So what could be tried next?

- [ ] Add more comments, logging, error handling.
- [ ] Restrict source DNs to be a SIP URI for attempting calls.
- [ ] Look into getting calling working potentially leveraging my Ooma Teleo or Astrisk on QNAP.
- [ ] Termination to a softphone client on iOS.     
  
