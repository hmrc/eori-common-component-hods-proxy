package integration;trait IntegrationTestSpec
        extends BaseSpec with ScalaFutures with BeforeAndAfterEach with BeforeAndAfterAll with GuiceOneAppPerSuite
                with WireMockRunner with MockitoSugar {

                SharedMetricRegistries.clear()

                val appConfig: Map[String, String] =
                        Map("microservice.services.auth.host" -> Host, "microservice.services.auth.port" -> Port.toString)

                implicit lazy val cc  = stubControllerComponents()
                val mockStubBehaviour = mock[StubBehaviour]

                def expectedPredicate(location: String): Predicate.Permission = Predicate.Permission(
                        Resource(ResourceType("eori-common-component-hods-proxy"), ResourceLocation(location)),
                        IAAction("WRITE")
                        )

                when(mockStubBehaviour.stubAuth(Some(expectedPredicate("get")), Retrieval.EmptyRetrieval)).thenReturn(Future.unit)
                when(mockStubBehaviour.stubAuth(Some(expectedPredicate("post")), Retrieval.EmptyRetrieval)).thenReturn(Future.unit)
                when(mockStubBehaviour.stubAuth(Some(expectedPredicate("put")), Retrieval.EmptyRetrieval)).thenReturn(Future.unit)
                when(mockStubBehaviour.stubAuth(Some(expectedPredicate("vat")), Retrieval.EmptyRetrieval)).thenReturn(Future.unit)

                implicit override lazy val app: Application = new GuiceApplicationBuilder()
                        .overrides(bind[BackendAuthComponents].toInstance(BackendAuthComponentsStub(mockStubBehaviour)(cc, global)))
                        .configure(appConfig).build()

                override def beforeAll(): Unit = startMockServer()

                override protected def beforeEach(): Unit = resetMockServer()

                override def afterAll(): Unit = stopMockServer()
                }
